#!/bin/bash

# Check if the number of files argument is provided
if [[ $# -ne 1 ]]; then
  echo "Please provide the number of log files as an argument."
  echo "Usage: ./sum_response_times.sh <num_files>"
  exit 1
fi

# Read the number of log files from the argument
num_files=$1

# Initialize the sum variables
bfs_sum=0
bidir_bfs_sum=0

# Loop through the log files
for ((i=0; i<$num_files; i++))
do
  filename="log$i.txt"
  
  # Extract the response times from the log file
  bfs_response_time=$(grep "INFO: Response time of BFS:" "$filename" | awk '{print $NF}')
  bidir_bfs_response_time=$(grep "INFO: Response time of Bi_Dir BFS:" "$filename" | awk '{print $NF}')
  
  # Add the response times to the sums
  bfs_sum=$((bfs_sum + bfs_response_time))
  bidir_bfs_sum=$((bidir_bfs_sum + bidir_bfs_response_time))
done

# Print the sums
echo "Sum of BFS response times: $bfs_sum"
echo "Sum of Bi_Dir BFS response times: $bidir_bfs_sum"
