run:
	./build/install/app/bin/app

run-test:
	./build/install/app/bin/app ./src/test/resources/file1.json ./src/test/resources/file2.json

build:
	./gradlew build

installDist:
	./gradlew installDist

report:
	./gradlew jacocoTestReport

sonar:
	./gradlew sonar --info

clean:
	./gradlew clean

PHONY: run run-test build installDist sonar clean
