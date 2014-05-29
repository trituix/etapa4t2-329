.PHONY: run

default:
	@echo -e "\e[1;42mCompilando Tarea 2 Elo329 14-1\e[0m"
	@javac -d . src/*.java
	@echo -e "\e[1;42mTarea 2 Compilada\e[0m"

run:
	@java PhysicsLab

doc:
	@echo -e "\e[1;42mGenerando documentacion\e[0m"
	@javadoc -d docs src/*.java
	@echo -e "\e[1;42mDocumentacion generada\e[0m"

clean:
	@echo -e "\e[1;42mEliminando archivos binarios\e[0m"
	rm *.class
	@echo -e "\e[1;42mEliminando archivos documentacion\e[0m"
	rm -rf docs