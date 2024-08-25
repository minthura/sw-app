[![Build](https://github.com/minthura/Codex/actions/workflows/build.yml/badge.svg)](https://github.com/minthura/Codex/actions/workflows/build.yml)

# MVVM Clean Architecture Multi-Module Android Project

![License](https://img.shields.io/badge/License-MIT-blue.svg)
![Platform](https://img.shields.io/badge/Platform-Android-brightgreen.svg)

## Table of Contents

- [Introduction](#introduction)
- [Architecture](#architecture)
- [Features](#features)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Introduction

This repository contains an Android application demonstrating the implementation of the MVVM (Model-View-ViewModel) architecture following clean code principles with a multi-module approach. The project is designed to be scalable, maintainable, and testable.

## Architecture

The project is structured around the MVVM architecture pattern, enhanced with clean architecture principles. The separation of concerns is achieved through distinct layers, each responsible for specific tasks:

- **UI Layer**: Contains Activities, Fragments, and ViewModels. Responsible for displaying data and handling user interactions.
- **Domain Layer**: Contains Use Cases and Domain Models. Encapsulates business logic.
- **Data Layer**: Manages data sources (e.g., local database, remote API) and repositories.

## Features

- Clean and maintainable codebase
- MVVM architecture with ViewModel and LiveData
- Dependency injection using Hilt
- Navigation component for fragment navigation
- Retrofit for networking
- Room for local database
- Coroutine support for asynchronous operations

## Contributing
We welcome contributions to enhance this project. Please follow these steps to contribute:

Fork the repository.
Create a new branch (git checkout -b feature/new-feature).
Commit your changes (git commit -m 'Add some feature').
Push to the branch (git push origin feature/new-feature).
Open a pull request.

## License
This project is licensed under the MIT License.

## Contact
For any questions, feel free to reach out:
Email: minthura.dev@gmail.com
