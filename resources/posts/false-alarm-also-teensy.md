It turns out that I jumped the gun putting up that dragon emblem[^dragon-emblem], so you won't be rid of me as soon as I was expecting. I did end up putting some hours into the PostScript generator port over to Common Lisp[^looks-about], but I also had to get *something* to play around with this weekend, in the absence of any Nords.

[^dragon-emblem]: Canada Post cheekily celebrates [Remembrance Day](http://en.wikipedia.org/wiki/Remembrance_Day), so I'll actually have to wait until Tuesday or so to actually get my hands on [my RPG fix](http://en.wikipedia.org/wiki/The_Elder_Scrolls_V:_Skyrim) for the year.
[^looks-about]: And it looks about as good so far, though I still need to get my head firmly around dictionaries.

Actually, now that I think about it, I'm not sure I ever mentioned my dalliance with embedded programming. In an effort to add a capitol C logo[^from-the-future] to the header there, and to have some fun with low-level electronics, I bought up an [Arduino Uno](http://www.arduino.cc/en/Main/arduinoBoardUno) a while ago[^the-old-one]. This is probably going to incinerate what little programmer cred I have, but I couldn't for the life of me get [the damn IDE](http://www.arduino.cc/en/Main/arduinoBoardUno) to actually upload a program to the chip. This was a while too; the board has been sitting there disused, still retaining the [default blink](http://www.arduino.cc/en/Tutorial/Blink) program it was shipped with. I've looked at various potential fixes including getting a more recent version from the repos, downloading the [official tarballs](http://www.arduino.cc/en/Main/software) from the Arduino site, and toying with some [command line](http://johanneshoff.com/arduino-command-line.html) utilities to program the thing without that fucking annoying little collection of big shiny icons. I officially give up. That removable Atmega328 may get re-purposed at some point, but I'm not sinking another hour into figuring out exactly why the programming tools don't seem to recognize the board.

[^from-the-future]: Hello from April of 2016! At this point, I've written enough things in C that I could truthfully add it to the logo bar if I wanted to. Having written my own Scheme, a couple simple garbage collectors, a Forth implementation, and a few embedded projects (some of which I've actually been paid for), bThere's a point to all this, I swear.

[^the-old-one]: The old one with the removable Atmega chip, rather than the new one with the tiny but soldered unit.

I mentioned that I wanted something else to keep me busy through the weekend[^as-if], so I ended up hitting up my local electronics store for a [Teensy 2.0](http://www.pjrc.com/teensy/). The funny part, to me at least, is that the little packing slip that came with this chip states

[^as-if]: As if I didn't already have enough projects in the air.

Programming Teensy with C provides access to all features. Experience with the C language and reading technical specifications is helpful. For a beginner oriented environment, [the] Arduino may be a better choice.

The reason *that's* slightly amusing is that, as I mentioned, the Arduino constantly drove me to frustration. By contrast, I got the Teensy up and running in under ten minutes by following the [Getting Started tutorial](http://www.pjrc.com/teensy/first_use.html). It boils down to

- Install the required libraries (`apt-get install gcc-avr binutils-avr avr-libc libusb-dev`)
- Download the loader program (either the [binary version](http://www.pjrc.com/teensy/loader_linux.html) for your architecture, or the [source](http://www.pjrc.com/teensy/loader_cli.html). Remember to run `make` if you go with the source version.)
- Plug in the Teensy
- Press the pushbutton to activate the HalfKay loader
- Load your code onto it (either by running the GUI, or by using `./teensy_loader_cli -mmcu=[your processor here] -wv program.hex`)

That's that.

I haven't actually started anything yet, but I plan to go through the listings of a few projects to warm up, hopefully culminating in a build of a [Chordite](http://chordite.com/) and addition of a new logo in a little while.
