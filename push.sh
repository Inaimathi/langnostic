rsync -r project.clj src test doc target LICENSE --exclude=*~ --exclude=*clj --progress inaimathi.ca:langnostic
rsync -rc resources/posts --exclude=*~ --progress inaimathi.ca:langnostic/resources
rsync resources/posts.json --progress inaimathi.ca:langnostic/resources
rsync -r --progress resources/public/ inaimathi.ca:langnostic/resources/public/
