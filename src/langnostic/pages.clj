(ns langnostic.pages
  (:require [markdown.core :as md]
            [hiccup.page :as pg]
            [clj-time.format :as fmt]
            [ring.util.codec :as cod]
            [cheshire.core :as json]

            [langnostic.auth :as auth]
            [langnostic.posts :as posts]
            [langnostic.comments :as comments]))

(defn post-href [post]
  (str "/posts/" (post :file)))

(defn post-links [post]
  [:div {:class "post-nav"}
   (if-let [prev (get @posts/posts (dec (:id post)))]
     [:a {:class "prev-post" :href (post-href prev)}
      "<-" (prev :title)])
   (if-let [next (get @posts/posts (inc (:id post)))]
     [:a {:class "next-post" :href (post-href next)}
      (next :title) "->"])])

(defn post-comments [post]
  (let [comment-tree (comments/get-comments-for (:id post))]
    (when (or auth/USER (not (empty? comment-tree)))
      [:div {:class "post-comments"}
       [:hr]
       [:h3 "Comments"]
       (map
        (fn rec [comment]
          [:div {:class "comment" :path (str (:path comment))}
           [:span {:class "comment-author"}
            [:img {:class "author-image" :src (get-in comment [:user :image])}]
            [:a {:class "author-link" :href (get-in comment [:user :url])} (get-in comment [:user :name])]]
           [:span {:class "comment-content"}
            (-> (:content comment)
                (clojure.string/replace "&" "&amp;")
                (clojure.string/replace "<" "&lt;")
                (clojure.string/replace "\"" "&quot;")
                md/md-to-html-string)]
           (when auth/USER
             [:form {:class "reply-form"
                     :action (str "/posts/" (:id post) "/comment/reply?path="
                                  (cod/url-encode (:path comment)))
                     :method "POST"}
              [:textarea {:name "comment"}]
              [:input {:type "Submit" :value "Reply"}]])
           (when (not (empty? (:replies comment)))
             [:div {:class "replies"}
              (map rec (:replies comment))])])
        comment-tree)
       (when auth/USER
         [:form {:class "post-comment-form" :action (str "/posts/" (:id post) "/comment") :method "POST"}
          [:textarea {:name "comment"}]
          [:input {:type "Submit" :value "Post"}]])])))

(defn post [post]
  [:div
   [:h1 [:a {:href (post-href post)} (:title post)]]
   [:span {:class "posted"}
    (fmt/unparse (fmt/formatter "E MMM d, Y") (:posted post))]
   (posts/post-content post)
   (post-links post)
   (post-comments post)])

(defn latest-post []
  (post (last @posts/posts)))

(defn archive [posts]
  [:div
   [:ul (map (fn [post]
               [:li [:a {:href (post-href post)} (post :title)]])
             posts)]
   [:h3 "Tags"]
   [:ul {:class "tags-list"}
    (map (fn [[tag count]]
           [:li
            [:a {:href (str "/archive/by-tag/" tag)} tag]
            "(" count ")"])
         (into (sorted-map) (frequencies (mapcat :tags posts))))]])

(defn nav-bar [section]
  [:div {:class "top-menu-container"}
   [:ul {:class "top-menu"}
    (map (fn [name]
           [:li (if (= name section)
                  name
                  [:a {:href (str "/" name)} name])])
         ["blog" "archive" "links" "meta" "tipjar" "feed"])
    [:li {:class "auth-button"}
     (if auth/USER
       [:span
        [:a {:href "/auth/log-out"} "logout"] " "
        [:span {:class "user-name"} (:name auth/USER)]
        [:img {:class "user-thumbnail" :src (:thumbnail auth/USER)}]]
       [:span {:class "login-menu"}
        [:a {:href "#" :class "placeholder"} "login"]
        [:a {:href (auth/login-url "patreon") :class "provider"} "patreon"]
        [:a {:href (auth/login-url "github") :class "provider"} "github"]])]]])

(def footer
  [:div {:class "license"}
   [:a {:rel "license" :href "http://creativecommons.org/licenses/by-sa/3.0/"}
    [:img {:alt "Creative Commons License" :style "border-width:0;float: left; margin: 0px 15px 15px 0px;"
           :src "https://i.creativecommons.org/l/by-sa/3.0/88x31.png"}]]
   [:p
    [:span {:xmlns:dct "https://purl.org/dc/terms/" :property "dct:title"}
     "all articles at langnostic"]
    " are licensed under a "
    [:a {:rel "license" :href "https://creativecommons.org/licenses/by-sa/3.0/"}
     "Creative Commons Attribution-ShareAlike 3.0 Unported License"]]
   [:p
    "Reprint, rehost and distribute freely (even for profit), but attribute the work and allow your readers the same freedoms. "
    [:a {:href "https://creativecommons.org/choose/results-one?license_code=by-sa&amp;jurisdiction=&amp;version=3.0&amp;lang=en&amp;field_format=&amp;field_worktitle=this+langnostic+article&amp;field_attribute_to_name=Inaimathi&amp;field_attribute_to_url=http%3A%2F%2Flangnostic.inaimathi.com&amp;field_sourceurl=http%3A%2F%2Flangnostic.inaimathi.com&amp;field_morepermissionsurl=&amp;lang=en_US&amp;n_questions=3"} "Here's"]
    " a license widget you can use."]
   [:p
    "The menu background image is "
    [:a {:href "https://www.flickr.com/photos/danzen/2360096926/in/photostream/"} "Jewel Wash"]
    ", taken from "
    [:a {:href "https://www.flickr.com/photos/danzen/"} "Dan Zen's"]
    " flickr stream and released under a "
    [:a {:href "https://creativecommons.org/licenses/by/2.0/"} "CC-BY license"]]])

(defn stylesheet [url]
  [:link {:rel "stylesheet" :href url :type "text/css" :media "screen"}])

(defn template [section page-title content]
  (pg/html5
   {:lang "en"}
   [:head
    [:title (str page-title " - langnostic")]
    [:link {:href "/feed/atom" :type "application/atom+xml" :rel "alternate" :title "Site-wide Langnostic Atom Feed"}]
    (stylesheet "/static/css/langnostic.css")
    (stylesheet "/static/css/default.css")
    [:script {:type "text/javascript" :src "/static/js/highlight.pack.js"}]
    [:script {:type "text/javascript"} (str "var user = " (json/encode auth/USER) ";")]
    [:script {:type "text/javascript" :src "/static/js/langnostic.js"}]
    [:script {:type "text/javascript"} "hljs.initHighlightingOnLoad();"]]
   [:body
    [:a {:href "/"} [:img {:class "logo-bar" :src "/static/img/langnostic.png"}]]
    (nav-bar section)
    [:div {:class "content"} content]
    [:hr]
    footer]))
