run:
	./build/install/app/bin/app

test-json:
	./build/install/app/bin/app ./src/test/resources/file1.json ./src/test/resources/file2.json

test-yaml:
	./build/install/app/bin/app ./src/test/resources/file1.yaml ./src/test/resources/file2.yaml

build:
	./gradlew build

test:
	./gradlew test

installDist:
	./gradlew installDist

report:
	./gradlew jacocoTestReport

sonar:
	./gradlew sonar --info

clean:
	./gradlew clean

PHONY: run run-test build installDist sonar clean
