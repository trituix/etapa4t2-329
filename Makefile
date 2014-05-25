.PHONY: run

default:
	javac -d . src/*.java

run:
	java PhysicsLab

clean:
	$(RM) *.class