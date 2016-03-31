JFLAGS = -g -d Output -classpath ./:Output/:Model/:Controller/:Misc/
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java


CLASSES = \
	JogWireless.java \
	Model/DBConnection.java \
	Model/Phone.java \
	Model/PhoneCall.java \
	Model/TextMessage.java \
	Model/InternetAccess.java \
	Model/Store.java \
	Model/InventoryItem.java \
	Model/Customer.java \
	Model/Plan.java \
	Model/Account.java \
	Model/PhoneNumber.java \
	Misc/Logger.java


default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) Output/*.class