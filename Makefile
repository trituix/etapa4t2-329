.PHONY: run

default:
	javac -d . *.java

run:
	java PhysicsLab

clean:
	$(RM) *.class