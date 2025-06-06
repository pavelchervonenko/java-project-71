run:
	./build/install/app/bin/app

run-test:
	./build/install/app/bin/app ./src/test/resources/file1.json ./src/test/resources/file2.json

clean:
	./gradlew clean

build:
	./gradlew installDist

install: clean build


.PHONY: run clean run-test build
