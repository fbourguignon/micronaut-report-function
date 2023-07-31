build:
	./gradlew build

docker-up:
	docker-compose up -d

docker-stop:
	docker-compose stop

docker-down:
	docker-compose down

run:
	@make docker-up
	sam build
	sam local invoke MicronautReportFunction -e ./events/generate-report.json --docker-network inventory_network
