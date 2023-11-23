# Neo4j-Live-Type-safe-UDPs

This repository contains the UDP code that was shown during the Neo4j Live session [Type and Refactoring Safety in User-Defined Procedures](https://www.youtube.com/watch?v=18yqaEXOuCU) on November, 23rd 2023. It demonstrates the usage of the Descriptor Framework [neo4jDescriptors](https://github.com/JensDeininger/neo4jDescriptors) on a very small and simple example.

## Getting started

To use the functions and procedures in this repository:

- Clone the [neo4jDescriptors](https://github.com/JensDeininger/neo4jDescriptors) repository.
- Build the neo4jDescriptors project.
- Clone this repository.
- Build this project.
- Go to the target folder (next to the src folder) of your cloned version of this repository and copy the jar file into the plugins folder of your Neo4j database. Then (re-)start your database.
- Create a basic graph in your database by copying the Cypher code from [graphSetup.cql](https://github.com/ElenaKohlwey/Neo4j-Live-Type-safe-UDPs/blob/main/src/test/resources/graphSetup.cql) into your Neo4j Browser.
- Now you can call any of the implemented procedures or functions on your database.
