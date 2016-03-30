JFLAGS = -g -d Output -classpath ./:Output/:Model/:Controller
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java


CLASSES = \
	JogWireless.java \
	Model/DBConnection.java


default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) Output/*.class