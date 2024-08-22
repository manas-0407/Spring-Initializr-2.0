
# SpringCommand-CLI

The Spring CLI Project is a command-line interface tool designed to streamline and automate common tasks involved in Spring Boot project development. This tool provides developers with a suite of commands to create, configure, and manage Spring Boot projects interactively and efficiently. By leveraging this CLI, developers can quickly set up projects, manage directories, handle application properties, log errors, and ensure that dependencies are up-to-date—all from the command line.


## Features

- Interactive Project Creation: Initiate new Spring Boot projects with ease using the spring init command.
- Directory Management: Navigate and manage project directories with commands like spring cd and spring root.
- Application Properties Configuration: Automatically add boilerplate code for dependencies to the application.properties file.
- Error Logging: Use the spring error command to fetch error details from a specified log file, combine these details with a user-provided description for quick error detection and correction.
- Dependency Version Checking: Check and update the dependencies in your pom.xml file to ensure your project is using the latest versions.


## Commands

- spring init : Initiates an interactive project creation session along with predefined templates.
        
- spring cd --root : Changes the directory to the specified root directory of project.

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `--root` | `String` | The root directory path to navigate to |



- spring cd .. : Clears the root directory, returning to the parent directory.




- spring root --ls :  Lists the contents of the root directory





- spring root :  Displays the current root directory path.






- spring properties --dependencies  : Adds boilerplate code for the specified dependencies to the application.properties file.

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `--dependencies` | `String` | A comma-separated list of dependencies to add. |




- spring error --file --description :  Command for automated error detection and correction.

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `--file` | `String` | The name of the log file to which the error will be logged. |
| `--description` | `string` |  The description or prompt associated with the error. |

- spring versionCheck --update :  Checks the pom.xml file for the latest dependency versions and optionally updates them.

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `--update` | `Boolean` | Set to True if you want to update the dependencies along with the version check. |

## Note

Add below code to your Target Project application.properties(or yml) file, so as to log error to a file

```bash
  logging.file.name=logfile.log
  logging.level.root=ERROR
```
