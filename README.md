# basicgame01

This is an attempt to write a basic game template with the uso of Canvas and a game loop.
The idea to write a game in native has been dropped in favor of LibGDX framework - but not before another attempt :P
This is an unfinished app that is currently using placeholder graphics.

Rules:
Red objects are enemies - their position is randomly generated every once in a while.
Gray objects are supposed to be collected - one appears after you collect one.
The more you collect the better of a collecter you have proven to be :P Im not giving you an award for this.
Objects are spawned at some distance from your sprite.

About controls.
Main Sprite moving pattern is interesting, as the Sprite can cover any point on the screen.
Note that is achived with just a single control event being recognized - a screen touch.

Apart from being unfinished it has several bugs.
 - events are dependant of a points counter state - and counter sort of not really properly work.
 - app keeps freezing.
 - and others.

