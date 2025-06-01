run-dist:
	./build/install/app/bin/app

run-test:
	./build/install/app/bin/app ./src/test/resources/file1.json ./src/test/resources/file2.json

clean:
	./gradlew clean

build-dist:
	./gradlew installDist

install: clean build-dist


.PHONY: run-dist clean build-dist run-test