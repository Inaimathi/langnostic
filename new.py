#!/usr/bin/python
import json
import os.path
from optparse import OptionParser
from subprocess import call, check_output


def wc_l(fname):
    return int(check_output(["wc", "-l", fname]).decode("utf-8").split(" ")[0])

def slugFromFname(fname):
    return os.path.basename(os.path.splitext(fname)[0])

def titleFromFname(fname):
    return " ".join(slugFromFname(fname).split("-")).title()

def main(fname, title, tags):
    slug = slugFromFname(fname)
    mod = int(os.path.getmtime(fname))
    newId = wc_l("resources/posts.json")
    rec = {'id': newId, 'title': title, 'file': slug, 'posted': mod, 'tags': tags}
    os.rename(fname, os.path.join("resources/posts", os.path.basename(fname)))
    with open("resources/posts.json", 'a') as f:
        f.write(json.dumps(rec))
        f.write("\n")

if __name__ == "__main__":
    parser = OptionParser()
    parser.add_option("-t", "--title", dest="title", default=False,
                      help="Manually specify the title (it'll otherwise be generated from the slug)")
    parser.add_option("-d", "--deploy", dest="deploy", default=False, action="store_true",
                      help="If this flag is present, automatically deploy to the server")
    (options, args) = parser.parse_args()
    if len(args) >= 2:
        main(args[0], options.title or titleFromFname(args[0]), args[1:])
        if options.deploy:
            call(["sh", "push.sh"])
    else:
        parser.print_help()
