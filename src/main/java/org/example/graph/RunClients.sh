#!/bin/bash

# Get the number of instances from the first argument, or use 5 if not provided
num_instances=${1:-5}

# Define the base command to run the Java program
java_command="java -jar Distributed_Project.jar"

# Loop through the number of instances and run the Java program in the background
# shellcheck disable=SC2004
for ((i=0; i<$num_instances; i++))
do
  # Append the i value to the Java command
  command_with_i="$java_command $i"
  $command_with_i &
done

# Wait for all instances to finish
wait
