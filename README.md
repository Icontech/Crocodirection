# Crocodirection
Simple AR Android app. Set your GPS position and choose a destination. Then point the camera towards the marker and a crocodile will point you in the right direction!

Crocodirection lets the user set his/her current latitude and longitude coordinates by using either GPS or a network connection. The user then chooses a destination where he/she wants to go. When the destination has been set the marker is scanned with the device's camera whereby a 3D crocodile accompanied by an arrow will show up on the device's screen. The arrow will point in the direction of the destination, telling the user where to go.

NOTES:
- The marker currently has to be facing north in order to show the correct direction.
- The "destinations.txt" file contains all the selected destinations. Add new destinations in the format:
longitude,latitude,Name_of_destination. Example: 10.752829,59.910267,Oslo
- The marker (crocoMarker.png) can be found in the root folder. Please print it yourself. :)

INFORMATION ABOUT THE PROJECT AND CONTRIBUTORS
- This application was first developed during a short course in Mixed Reality Systems at Osaka University in 2014 by me (David Sandberg) and Edouard Lagrue. The original idea was to put several markers on campus which the students could scan if they were lost, in order to find the desired directions. I was responsible for the coding while Edouard created the marker and came up with ideas regarding how to calculate the angle from the marker to the chosen destination. The crocodile model was created by Hendrik Lorbeer. Bugfixes and code clean up was done in January 2016.

- The Crocodirection app uses the AndAR Toolkit (https://code.google.com/archive/p/andar/) to render graphics and recognize markers etc.

- The classes in the graphics, models, parser and util folders were taken from the AndObjViewer project (http://andar.googlecode.com/svn-history/r77/trunk/AndObjViewer/src/edu/dhbw/andobjviewer/) and then modified to fit the Crocodirection app. The AndObjViewer was created by Tobias Domhan (https://github.com/tdomhan).

------
Please try the app and feel free to modify and add any functionality you want! (the app is distributed under the GNU GPLv3 License: http://www.gnu.org/licenses/quick-guide-gplv3.html).

Let me know if you have any questions.

Best regards,

David Sandberg 
https://github.com/Icontech
