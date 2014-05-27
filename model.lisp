(in-package :langnostic)

(defparameter *base* (fact-base:load! #p"langnostic.base"))

(defmethod read-chronology ()
  (with-open-file (s "blog-chronology.lisp")
    (reverse (read s))))

(defmethod write-chronology (cron)
  (with-open-file (s "blog-chronology.lisp" :direction :output :if-exists :supersede :if-does-not-exist :create)
    (format s "~s" cron)))

(defmethod load-all! ((dirname pathname))
  (let ((base (make-fact-base :file-name "langnostic.base")))
    (loop for (title file tags date) in (read-chronology)
       for path = (merge-pathnames file dirname)
       do (with-open-file (s path)
	    (let ((buf (make-string (file-length s))))
	      (read-sequence buf s)
	      (multi-insert! base `((:title ,title) (:file ,file) (:edited ,(file-write-date path)) (:body ,buf) (:posted ,date)
				    ,@(mapcar (lambda (tag) `(:tag ,tag)) tags))))))
    base))

(defmethod insert-article! ((base fact-base) (file pathname) (tags list) (body string))
  (for-all (?id :current t) :in *base* :do (delete! *base* (list ?id :current t)))
  (multi-insert! `((:title ,(->title file)) (:file ,(file-namestring file)) (:edited ,(file-write-date file)) (:body ,body) (:posted ,(get-universal-time))
		   ,@(mapcar (lambda (tag) `(:tag ,tag)) tags))))

;; TODO - write update-article!

(defun all-tags ()
  (let ((hash (make-hash-table)))
    (for-all (?id :tag ?tag) :in *base* 
	     :do (incf (gethash ?tag hash 0)))
    (sort (alexandria:hash-table-alist hash) #'string<=
	  :key (lambda (pair) (symbol-name (car pair))))))
