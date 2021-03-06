So I finally got a new machine.

A Lenovo x220 laptop. It's kind of a lightweight, but even with an i3 and only 4gb of ram, it easily doubles the specs of my netbook (RIP, battery). Speaking of which, I also got a 9-cell battery for the new box. The cost of this unit was much lower than usual because of a promotional code I stumbled upon. I haven't actually had time to take it for a spin, but the battery came about 20% charged and told me that this would be enough for 2:30 hours of operation. That. Is. Awesome.

The drive in it still has Windows on it so I didn't poke around much like I said. My plan is to toss an SSD in there and throw in Debian as usual[^the-docs-tell]. That hard drive is going to be a pain in the ass though. It *is* a standard 2.5 inch SATA drive, but it's very *slightly* shorter than the OCZ units I have lying around. That leaves me three options

[^the-docs-tell]: The documentation tells me that I'll actually need to set up one piece of non-free software to run my wireless card, which is sad, but I don't really have the time or patience to go hunt down an atheros-based card this week.

1. Go buy a new SSD for the laptop _(plausible in a little while, but I'm sort of shopped out at the moment)_
2. Mod the laptop to accept slightly taller drives _(this kills the warranty, and the laptop is still covered so I don't want to fuck with it)_
3. Mod one of my drives to fit a smaller space _(this also kills the warranty, but my drives are long out of their warranty period, so I give precisely zero fucks)_

Three is the obvious winner for the short term. I may still go get a new hard drive later; this unit supports SATA3, so I can get an extra little speed boost by shelling out. Sadly, it looks like SATA3 drives don't come smaller than 120GB, which means ~$200 or so. Incidentally, a [SATA2 32GB drive is down to the $60 range](http://www.newegg.ca/Product/Product.aspx?Item=N82E16820227393) these days, so if you're a Linux user that still has a 7200RPM, or even a 10k, you may want to pick an SSD up at these prices. The difference is noticeable.

## Time Passes...

So that's done

![A picture of my x220 booting from the new hard drive](/static/img/x220-done.jpg)

Except that "mod" turned out to be a bit of an overstatement[^which-is-why]. I literally just removed the case[^which-is-not], braced it in the laptop drive bracket using some rubber stoppers, and secured the whole thing with some electrical tape. I'm not about to get fancy for an internal component; it just needs to work, and this does.

[^which-is-why]: Which is why you don't see pictures.
[^which-is-not]: Which is not nearly as dangerous for the drive on an SSD as it is on a traditional platter drive.

That's the old install of AMD64 Squeeze I already had on the drive, and it works mostly fine but I'll still need to re-install. [The ethernet drivers in Squeeze don't support Intel's 82579LM](http://bugs.debian.org/cgi-bin/bugreport.cgi?bug=626220); it's already been patched in [Wheezy](http://www.debian.org/devel/debian-installer/). There obviously aren't any plans to push new changes to the stable release, so I guess I'm moving up to testing. I don't anticipate any problems; [`unetbootin`](http://unetbootin.sourceforge.net/) is running as we speak[^those-of-you].

[^those-of-you]: Those of you who already have a Debian machine set up can actually just `apt-get install unetbootin`

I have done a little bit of typing on it by this point; my intention was to finish this article with it, but the lack of network connectivity of any kind put a stop to that. I did notice that the keyboard was improperly set; the right side was ok, but the left side was actually vibrating with each keystroke. Undoing the keyboard screws on the back and re-setting it fixed that easily.

Installing Debian is pretty close to a non-event around here in terms of the attention it requires. Really, I've been thinking about prototyping some things. Firstly, the password-logger is proceeding apace, though I still don't have enough done to consider publishing. I'm at the stage where I can have it remember multiple passwords, cycle through them, and output them on a single button-press. Right now I'm playing around with a little 1x11 numeric LED display I picked up so that the unit will be able to display a label for each password, and I still want to put it together in such a way that I can add/change/delete passwords without having to recompile the thing.

## In Other News

You already know that my plans involved building a prototype of the [chordite](http://chordite.com/), but I've also been looking at other layouts and ideas for the same purposes. I'm only about half-way through building a chordite, but here are my preliminary thoughts about all the designs I've taken a look at[^ideally].

[^ideally]: Ideally, I'll actually put together one of each and take them for a test-drive, but I have no idea how long that will actually take me.

### Chordite

**Pros:**

- one-handed *(that lets you use the other hand for balance, for example, in a public transit situation)*
- data entry doesn't use fingertips *(makes it easy to chord)*
- [standard configuration](http://chordite.com/license.htm) covers 84 keys *(the default key mapping reference is in that zip file)*
- simple construction *(only 8 switches to wire up, and the rest can be built with a bit of cardboard and some wire coathanger)*

**Cons:**

- chords entered in serial *(so it doesn't seem like you could easily use Emacs keystrokes of the style `C-x C-h`, and it seems like this would kneecap your speed since you're doing two or three effective key-presses per keystroke)*
- strapless *(which may or may not be comfortable, but it does mean that you thumb needs to support the board rather than being available for its own keystrokes)*


### [joestutes Wearable Keyboard](http://www.youtube.com/watch?v=URqYG-iMcTY#t=02m10s)

**Pros:**

- one-handed
- non-chording *(each switch is one key; I'm assuming that this would be more accurate and faster than chording; it also seems like it would make it easier to memorize)*
- provides "mouse" through thumb-operated joystick

**Cons:**

- emulates 40 keys total, so you need a reduced character set; this is not a coding keyboard
- requires high level of precision control from each finger *(I'm actually not even sure if this is a human thing, or just my mutant hands, but I can only reliably do fine-grained independent control with my index and middle finger; the ring and pinky fingers, even on my main hand, can't really move on their own very well and almost certainly wouldn't be able to do the sort of multi-directional movement that this thing seems to need)*
- construction involves wiring 40 separate switches and a joystick. That's not exactly simple.


### [Ergo Electronics Keyglove](http://www.youtube.com/watch?v=0I3jZZrPbPs&feature=related)

**Pros:**

- tons of keys
- glove-based *(you're not actually holding anything, so you can easily switch between typing and doing something else)*
- one-handed

**Cons:**

- requires high-precision movements with all fingers *(it works with some fairly densely packed contacts, and assumes you can hit them, with various levels of hand-contorting movements, with each finger)*.
- single keystrokes, so doing Emacs chords seems difficult if not impossible


### [Carsten Mehring Keyglove (first prototype)](http://www.youtube.com/watch?v=zr1kqL08uj4#t=02m00s)

**Pros:**

- tons of keys
- glove-based
- Emacs-style chord capability

**Cons:**

- two-handed *(even though you can switch between things easily, you need both hands dedicated to typing with this one; not sure if it would work that way in practice)*
- demo video translation is typeset set in Comic Sans *(I'm still a designer, dammit)*


### [Carsten Mehring Keyglove (second prototype)](http://www.youtube.com/watch?v=zr1kqL08uj4#t=02m43s)

**Pros:**

- glove-based
- one-handed

**Cons:**

- not entirely sure how the keystrokes are generated, and therefore how many can actually be generated *(it looks like it doesn't do Emacs chords either)*


### [Twiddler](http://www.youtube.com/watch?v=zZhWa2FfEac&feature=related)

**Pros:**

- one-handed
- strapped *(so your thumb is used for modifier keys)*
- provides "mouse" *(mini joystick operated by thumb)*
- thumb operates modifier keys *(so Emacs-style chords are easy to hit)*

**Cons:**

- none that I can see, actually

Like I said, these are very preliminary thoughts. It's possible that actually using some is easier/more comfortable than I imagined based on the demo videos. A priori, it looks like the ideal is a strap-using, portable-joystick-style keyboard, *or* a two-handed glove model. Granted I've got different problems than the average user. Emacs use means I need to be able to chord multiple keys, and Tiling WM/keyboard reliance means I don't really want a pointing device most of the time.
