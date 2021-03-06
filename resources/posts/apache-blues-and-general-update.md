Apache.

Sucks.

Balls.

It took me about four hours today to figure out why the hell I couldn't use `localhost` to test pages in my home directory. It's never come up before because every single other computer I've set this up on has just worked right off the bat. It turns out that what I had to do is run the command `chmod o+x ./` in the home directory. So I guess this is actually an example of how Unix sucks balls? For the record, I realize that I'm a bit more ham-handed than the average server-admin at handling my OS and permissions, but I refuse to believe that it was *all* my damn fault. There's nothing anywhere that might have tipped me off to what was happening. A terribly generic error message, a completely useless error dump in the logs (literally more generic than the error message), and a [bunch](http://httpd.apache.org/docs/1.3/misc/FAQ.html#malfiles) of [documentation/discussion](http://www.spiration.co.uk/post/1195/You%20don't%20have%20permission%20to%20access%20root%20on%20this%20serv) that gave flat-out incorrect responses, or pointed me in precisely the wrong direction. This is probably just because I spend so much of my time thinking about usability, but the affordance level here is awful.

You literally need to know about the magic button in order to fix a problem like this. There's nothing that indicates this disease given the symptom, and the solution is peripheral and non-obvious. This is quite possibly the worst way to design software, and all it really did was force me to redouble my efforts to [learn](http://www.google.ca/search?aq=f&sourceid=chrome&ie=UTF-8&q=yaws) [about](http://docs.plt-scheme.org/web-server/index.html) [alternatives](http://opensource.franz.com/aserve/) to this giant mud-ball called a server[^not-that].

[^not-that]: Not that AllegroServe is any better by most accounts, but's still worth a look if you're running your app in Common Lisp

Other than that, going pretty well. Kicking ass and taking names at the new place. I've written up a draft of my thoughts, but I sort of deliberatly haven't published them yet. It seems like I'd need a bit more time to get the lay of the land around here before I can come to any conclusions on how things are going to go, or even whether I made the formally correct decision leaving I Love Rewards. I'm leaning towards "yes" on the latter, but we'll see. There's this objective affectation that I like to adopt, specifically because it's a bit tougher to think in those terms about stuff like major life decisions. I don't want to get into the endlessly self-reenforcing pattern that humans tend to display, but I also don't want to gimp my chances at a new place by assuming things are worse than they are.

For the moment, I'm looking at this as an excellent opportunity. It's a place that's just displaying a nascent hint of IT-centricity (in a good way), and I want to do absolutely everything in my power to make sure this place goes in the right direction. Not necessarily *my* direction, but I get the feeling I can ease a lot of growing pains if I make the right choices, and there are definitely leapfrog processes and technologies I can share.

The only thing I'm sure of is that it'll be interesting.
