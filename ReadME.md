#Project Wifi ReadME

<b>------------------------------------------------ Wifi Project ------------------------------------------------<BR></b>
This Project was made By the Authors :  Gal Turgeman , Mark Antelis ,Mark Gurin.

<b>-----------------------------------------------About The Project----------------------------------------------<Br></b>
The project was build on the base of data analyzing from Android app, 
The app collect Wifi signals that was in the radius of the client , after the collecting it was export to txt file
reprocessing in the our java project to csv file and finally exported to kml file , that can include any of the following options
1)filtered by given Location s.t the point latitude  longitude 
2) filtered by the Time 
3) filtered by the Name of the device.

<b>-------------------------------------------------Installation -------------------------------------------------<br></b>
in the project you can find the src folder , the toRead and the toWrite folders.
the src folder is the source folder that contains the java files.
the toRead folder is represents the files that you want to be scanned and the toWrite folder will contain the final files s.t kml file and the filtered csv file

<b>-------------------------------------------------Java Classes -------------------------------------------------<br></b>
INIT the class where you can find all paths to folders of the project 
Wifi the class that represent our Wifi member (every wifi has the MAC , ID , channel , Signal Rate , etc )
ReadFile - Reading the folder and collect all csv files and create collection of wifi members and also the csv files 
	this collection stays at the same structure until the end of the program , in other words once we read them we can use 
	them in other ways.
WriteFile - first it calls the readFile , after the reading is complete the writing process is activating. 
	at the end we will have one file that is the union of all files that was readed from the toRead folder, in that csv file 
	the wifi's will be organized by the following rules : each timeline will represent the collections of the wifis that have that same time
	moreover that wifis will be sorted in that line by the decreasing order form the highst value of the Signal to the smallest one.
	NOTE** that the use sets the number of wifis per each line in the INIT.java file.
KmlWrites - writes the given collection of wifis to kml file. (using JAK directory)
Filters - that functions send to the kmlWriter the collections to write .<BR><BR><BR><BR>
<B>------------------------------------------------ScreenShots---------------------------------------------------</B>
<a href="http://tinypic.com?ref=wbxrar" target="_blank"><img src="http://i65.tinypic.com/wbxrar.jpg" border="0" alt="Image and video hosting by TinyPic"></a>
<BR>
<a href="http://tinypic.com?ref=23kckdl" target="_blank"><img src="http://i64.tinypic.com/23kckdl.jpg" border="0" alt="Image and video hosting by TinyPic"></a>
<BR>
<a href="http://tinypic.com?ref=35816xk" target="_blank"><img src="http://i65.tinypic.com/35816xk.jpg" border="0" alt="Image and video hosting by TinyPic"></a>
<BR>
<a href="http://tinypic.com?ref=67ikvt" target="_blank"><img src="http://i63.tinypic.com/67ikvt.jpg" border="0" alt="Image and video hosting by TinyPic"></a>


