Immediate:
## Design
It probably would have been far better to use a bot API that runs on the same machine as the instance of Starcraft, and add network functionality to that, instead of using the ProxyBot, because there is far more code to work with.
I was tempted to have all of our code in a space outside of the VM...
There are only a handful of bots that are implemented that way that we can use for reference.


###Not too important:
Clean up VM, and make it immutable.
Share via Google Drive.

###Possible features for later:
 - There are some very promising Processing visualizations that we should consider.
Processing sketches can be easily integrated.



###Existing ProxyBots that can be toggled:

#####bwmas

[bwmas Starcraft Multiagent system](https://code.google.com/p/bwmas/)
Local repo
<https://github.com/unqueued/bwmas/tree/master/scbwai/ProxyBot-2.6.1/ProxyBot>

Requires Java JADE
<http://www.iro.umontreal.ca/~vaucher/Agents/Jade/JadePrimer.html>

bwmas specifically requires it.fbk.sra.ejade.jadeNature
<http://selab.fbk.eu/dnguyen/ejade/download.html>

How to get it working in eclipse:
<http://stackoverflow.com/questions/8375143/java-eclipse-how-do-i-change-the-classpath>

#####legbot

[legbot](https://wiki.csc.calpoly.edu/rdrees-Wesnoth/wiki)
Looks promising

##### StacraftGP
[StarcraftGP](https://github.com/fairfieldt/StarcraftGP)

Probably not what we want.
This agent is just part of a larger genetic algorithm.