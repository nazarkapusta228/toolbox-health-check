
# ***🛠️ Toolbox Health Check***

## 📋 Table of Contents

✨ Features

🏗️ Architecture

🚀 Quick Start

🐳 Docker Deployment

⚙️ Configuration

🧪 Testing

📊 CI/CD Pipeline

⚠️ Known Issues
#





## ✨ Features

### _Core Monitoring_

Docker Health - Checks Docker installation, version, running containers, and daemon status

Git Status - Monitors Git repository existence, current branch, and last commit

System Metrics - Tracks CPU usage, memory consumption, and disk space

Unified Health Dashboard - Single endpoint aggregating all system checks

### _Technical Stack_

Backend: Spring Boot 4.0.1 with Java 25

Build Tool: Apache Maven with dependency management

Containerization: Docker with Alpine Linux base image

CI/CD: GitHub Actions with automated testing pipeline

API Documentation: SpringDoc OpenAPI 3.0 integration

Code Quality: Lombok for cleaner code, JUnit 5 for testing



#

## 🏗️ Architecture
The application follows a modular microservices-inspired architecture within a single Spring Boot application:

### Service Layer Architecture:

DockerHealthService: Executes Docker CLI commands to gather container information

GitStatusService: Checks Git repository status and commit history

SystemMetricsService: Collects system resources using Java Management Extensions

HealthStatusService: Aggregates all health checks into unified response


#

## 🚀 Quick Start
### _Prerequisites Installation_
Ensure you have the following installed on your system:

Java Development Kit (JDK) 25 or higher

Apache Maven 3.6 or newer

Docker Desktop (for containerized deployment)

Git for version control

### _Local Development Setup_
1.Clone the repository to your local machine

git clone https://github.com/nazarkapusta228/toolbox-health-check.git
###
2.Navigate to the project directory

cd toolbox-health-check
###
3.Build the project using Maven

mvn clean package
###
4.Run the Spring Boot application

java -jar target/toolbox-health-check-*.jar 
###
5.Access the health dashboard through your web browser or API client

curl http://localhost:8080/api/health

Or open in browser: http://localhost:8080/api/health
###
###
Using Docker:
1.Build Docker image
docker build -t toolbox-health-check .
###
2.Run container
docker run -p 8080:8080 toolbox-health-check

###
### _Access Points_
Once running, the application provides several access points:
Primary health endpoint at /api/health
Individual service health checks
Spring Boot Actuator endpoints for detailed metrics
Swagger UI for interactive API documentation


#

## 🧪 Testing
Testing Strategy

The application implements a comprehensive testing strategy covering multiple layers:

### _Unit Testing:_

Service layer testing with mocked dependencies

Model validation and serialization tests

Utility method and helper function tests

### _Integration Testing:_

API endpoint integration tests

Service integration with external commands

Configuration validation tests

### _Test Automation:_

Automated test execution in CI/CD pipeline

### _Test Architecture_
Tests are organized following the same package structure as main code. Mock objects are used extensively to isolate components and ensure reliable test execution.

### 

Factory methods create consistent test objects

Test configurations are isolated from production

Cleanup procedures ensure test independence

### _Continuous Testing_
Tests are automatically executed:

During pull request validation

In the CI/CD pipeline before deployment

#

## 📊 CI/CD Pipeline

The GitHub Actions CI/CD pipeline implements continuous integration and deployment with the following stages:

### _Build Stage:_

Code checkout with full Git history

Java 25 environment setup with Maven caching

Project compilation and packaging


### _Test Stage:_

Unit test execution with JUnit 5

### _Docker Stage:_

Docker image building 

Image tagging with commit hashes and version labels

Optional push to container registry

Image validation and testing

#

### _Pipeline Triggers_
The pipeline executes automatically on:

Push events to main branches

Pull request creation and updates

Manual triggers via workflow dispatch


#
### _Quality Gates_

The pipeline includes several quality checks:

Compilation success requirement

Minimum test coverage thresholds

## ⚠️ Known Issues

_Problem Context:_

When the application runs inside a Docker container, it cannot directly access the Docker daemon running on the host. This creates a fundamental limitation for Docker health monitoring from within containers.

_Technical Challenge:_

Docker commands (docker ps, docker info) executed from inside a container fail because:

Docker CLI is not installed in the application container

Container isolation prevents direct host system access

_Current Implementation:_

The application detects when Docker is unavailable and returns appropriate status messages indicating the limitation rather than failing silently.
