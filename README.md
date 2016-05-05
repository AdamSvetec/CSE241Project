Adam Svetec (ajs217)
CSE241
Final Project

RUNNING APPLICATION

Using "java -jar ajs217.jar" should work fine. A run script is also provided and can be used using the command "./run".

LOGGING IN

The initial login to the application will ask for my Oracle username and password. That is ajs217 and whatever password has been reset to.

INTERFACE OPTIONS

There are two separate interface options to choose from upon logging in. 

The first is an interactive interface that is meant to be used either by an employee helping a customer, or possibly even by a customer themselves. This interface allows the user to add an account, customer, phone, and phone number to our system. Each action walks the user through the steps to complete it. While type checks are done as the data is entered by the user, many inconsistency checks are not done as data is entered, but are instead done when the user selects to save their new creation.

The second is a reader that allows an employee to read in a csv file of usage records. This file is specified in a path, and the TestingCSV.txt file can be used to display outcomes. The format of the file is explained in the sample file itself and also contains a number of intentionally incorrect entries. The summary of the import is then displayed on the screen.

COMPILING APPLICATION

To compile the application, because of the slightly complex nature of the source directories, please use the provided makefile. This can be run using the command, "./make" from the ajs217svetec directory. Note all of the command from the bash script can be executed individually to test if there are any doubts. By nature, the actual jar file is compiled into the Application/Output/ directory and is then copied into the top-level directory along with the ojdbc6.jar file. 

SOURCE CODE

All source code is located in the ajs217 directory as specified in the directions, however these are simply a copy of all source files in the Application directory.

The source code is structured in the following manner:

Controllers: This directory contains the controllers for the two interfaces as well as the login and interface selector.
Libraries: This contains the ojdbc6.jar file which is copied into the Output directory upon build. It also contains the manifest file which is used to create the jar executable.
Misc: This contains a number of extra classes. DateFormatter class works to format java Date objects into the form recognized by Oracle. The DBPopulator class has a main method that can be run to repopulate the data in all the tables (although the populate methods are stored in the java Model classes themselves). Lastly, the Logger is used to write all exceptions caught, errors encountered, or other runtime info into the RuntimeLog.txt file (so that they are not directly displayed to the user).
Model: This folder contains all of the wrapper java classes containing all of the database table operations. DBConnection class also exists as an adapter to the java Connection class.
Output: This is the folder where all of the compiled java files are placed so that they can be run. For your purposes the essential ajs217.jar and ojdbc6.jar files are copied into the top-level directory.
Views: This contains some standard command line utilities for easier I/O for the controllers.

The application's main method is stored in the JogWireless class.

DATA GENERATION

To regenerate the data if so desired, please use the command "./run populate" from the command line.

TRIGGERS

Triggers were added in PLSQL for facilitating the insertion of Account, Customer, Phone Call, Text Message, and Internet Access. Because all of these relations have arbitrary id's, whenever a tuple is inserted it adds one to the currently highest id and inserts it with that new id.