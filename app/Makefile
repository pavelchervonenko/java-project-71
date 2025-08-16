run:
	./build/install/app/bin/app

test-json-stylish:
	./build/install/app/bin/app ./src/test/resources/file1.json ./src/test/resources/file2.json

test-yaml-stylish:
	./build/install/app/bin/app ./src/test/resources/file1.yaml ./src/test/resources/file2.yaml

test-nested-json-stylish:
	./build/install/app/bin/app ./src/test/resources/nested_file1.json ./src/test/resources/nested_file2.json

test-nested-json-plain:
	./build/install/app/bin/app ./src/test/resources/nested_file1.json ./src/test/resources/nested_file2.json -f=plain

test-json-json:
	./build/install/app/bin/app ./src/test/resources/file1.json ./src/test/resources/file2.json -f=json

test-nested-yaml:
	./build/install/app/bin/app ./src/test/resources/nested_file1.yaml ./src/test/resources/nested_file2.yaml

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

PHONY: build