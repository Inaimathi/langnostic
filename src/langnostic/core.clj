(ns langnostic.core
  (:require [clojure.edn :as edn]
            [org.httpkit.server :as server]
            [cheshire.core :as json]
            [org.httpkit.client :as http]
            [compojure.route :as route]
            [clojure-watch.core :as watch]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.session :refer [wrap-session]]

            [clojure.java.io :as io]

            [langnostic.auth :as auth]
            [langnostic.feed :as feed]
            [langnostic.pages :as pages]
            [langnostic.posts :as posts]
            [langnostic.files :as fs]
            [langnostic.comments :as comments])
  (:use [compojure.core :only [defroutes GET POST DELETE ANY context]])
  (:gen-class))

(defn error-404
  []
  {:status 404
   :headers {"Content-Type" "text/html"}
   :body (pages/template
          name name
          (fs/file-content
           "resources/public/content/404.md"))})

(defn static-page [name]
  (fn [req]
    (let [file (io/file "resources/public/content" (str name ".md"))]
      (binding [auth/USER (get-in req [:session :user])]
        (if (fs/file-in-resources? file)

          {:status 200
           :headers {"Content-Type" "text/html"}
           :body (pages/template file (clojure.string/capitalize name) (fs/file-content file))}
          (error-404))))))

(defn post [name]
  (fn [req]
    (binding [auth/USER (get-in req [:session :user])]
      (if-let [post (posts/find-by-slug name)]
        {:status 200
         :headers {"Content-Type" "text/html"}
         :body (pages/template "blog" (post :title) (pages/post post))}
        (error-404)))))

(defn home [req]
  (binding [auth/USER (get-in req [:session :user])]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (pages/template
            "blog" "Welcome"
            [:div
             (fs/file-content "resources/public/content/intro.md")
             [:hr]
             (pages/latest-post)])}))

(defn archive [posts]
  (fn [req]
    (binding [auth/USER (get-in req [:session :user])]
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body (pages/template
              "archive" "Archive"
              (pages/archive posts))})))

(defn atom-feed [posts]
  (fn [req]
    {:status 200
     :headers {"Content-Type" "application/atom+xml"}
     :body (feed/atom-feed posts)}))

(defn authenticate [auth-type]
  (fn
    [req]
    (let [user (auth/authenticate! auth-type (get-in req [:params "code"]))]
      {:status 303
       :headers {"Location" "/"}
       :session {:user user}})))

(defn post-comment
  [req]
  (let [user (get-in req [:session :user])
        {post-id :id content "comment" path "path"} (:params req)
        post-id (edn/read-string post-id)
        path (edn/read-string path)]
    (when user
      (if path
        (comments/post-comment! user post-id path content)
        (comments/post-comment! user post-id content)))
    {:status 303
     :headers {"Location" (get-in req [:headers "referer"])}}))

(defn log-out
  [req]
  {:status 303
   :headers {"Location" "/"}
   :session nil})

(defn dummy-user
  [req]
  {:status 303
   :headers {"Location" "/"}
   :session {:user {:site "patreon"
                    :name "inaimathi" :url "https://inaimathi.ca"
                    :image "/static/img/wonka.jpg" :thumbnail "/static/img/wonka.jpg"
                    :pledges []}}})

(defroutes langnostic-routes
  (GET "/" [] home)
  (GET "/dev/dummy-user" [] dummy-user)
  (GET "/blog" [] home)
  (GET "/posts/:name" [name] (post name))

  (POST "/posts/:id/comment" [] post-comment)
  (POST "/posts/:id/comment/reply" [] post-comment)

  (GET "/auth/log-out" [] log-out)
  (GET "/auth/:auth-type" [auth-type] (authenticate auth-type))

  (GET "/archive" [] (archive (posts/all-posts)))
  (GET "/archive/by-tag/:tag" [tag] (archive (posts/find-by-tag tag)))

  (GET "/links" [] (static-page "links"))
  (GET "/tipjar" [] (static-page "tipjar"))
  (GET "/meta" [] (static-page "meta"))

  (GET "/feed" [] (atom-feed (posts/all-posts)))
  (GET "/feed/atom" [] (atom-feed (posts/all-posts)))
  (GET "/feed/atom/:tag" [tag] (atom-feed (posts/find-by-tag tag)))
  (GET "/feed/atom/by-tag/:tag" [tag] (atom-feed (posts/find-by-tag tag)))

  (route/resources "/static/")
  (route/not-found (fn [req] (error-404 (get-in req [:session :user])))))

(defn -main
  ([] (-main "8000"))
  ([port]
   (println "Loading posts...")
   (posts/load-posts!)

   (println "Watching FS resources...")
   (watch/start-watch
    [{:path "resources/"
      :event-types [:create]
      :callback (fn [event filename]
                  (when (= "resources/posts.json" filename)
                    (println "Reloading posts.json ...")
                    (posts/load-posts!)
                    (println "  posts.json reloaded...")))}
     {:path "resources/posts/"
      :event-types [:create :modify]
      :callback (fn [event filename]
                  (let [name (.getName (io/file filename))
                        slug (.substring name 0 (- (count name) 3))
                        post (posts/find-by-slug slug)]
                    (when post
                      (println "Poking cache for" slug "...")
                      (reset! (post :content) nil))))}])

   (println "Listening on port" port "...")
   (server/run-server
    (-> langnostic-routes
        wrap-params
        wrap-session)
    {:port (read-string port)})))
