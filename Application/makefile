JFLAGS = -g -d Output -classpath ./:Output/:Model/:Controller/:Misc/
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java


CLASSES = \
	JogWireless.java \
	Model/DBConnection.java \
	Model/Phone.java \
	Misc/Logger.java


default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) Output/*.class