(in-package :langnostic)

(defmethod ->title ((fname pathname))
  (->title (pathname-name fname)))

(defmethod ->title ((fname string))
  (string-capitalize (substitute #\space #\- fname)))

(defmethod truncat ((thing string) (len number))
  (if (> (length thing) len)
      (let* ((short (subseq thing 0 len))
	     (last-space (position #\space short :from-end t)))
	;; (string-right-trim "()[]{}-_+=,.;'" )
	(concatenate 'string (subseq short 0 last-space) "..."))
      thing))

(defmethod rss-timestamp ((u-time number))
  (local-time:format-rfc3339-timestring t (local-time:universal-to-timestamp u-time)))

(defun link-list (&rest uri/name-list)
  (with-html-output (s *standard-output*)
    (:ul (loop for (uri name) on uri/name-list by #'cddr
	    do (htm (:li (:a :href uri (str name))))))))
