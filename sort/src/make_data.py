import numpy as np 
import os
import random
from argparse import ArgumentParser

def generate_large_file(number_of_files, file_path, max_size_bytes, min_number, max_number):
    for file_number in range(number_of_files):
        with open(file_path + str(file_number) + ".txt", 'w') as file:
            # Continue writing until the file size is just under 1 GB
            while os.path.getsize(file_path + str(file_number) + ".txt") < max_size_bytes:
                # Write a random number followed by a newline
                file.write(f"{random.randint(min_number, max_number)}\n")

def dist(l, k):
    rand = random.uniform(0,1)
    if rand <= 0.9:
        return l
    elif rand > 0.9:
        return k
    
def generate_large_files_from_low_entropy_distribution(number_of_files, file_path, max_size_bytes, min_number, max_number):
    l_s = [random.randint(min_number, max_number) for _ in range(1000)]
    r_s = [random.randint(min_number, max_number) for _ in range(1000)]
    # print(l_s)
    for file_number in range(number_of_files):
        with open(file_path + str(file_number) + ".txt", 'w') as file:
            # Continue writing until the file size is just under max_size_bytes
            while os.path.getsize(file_path + str(file_number) + ".txt") < max_size_bytes:
                for i in range(len(l_s)):
                    file.write(f"{dist(l_s[i], r_s[i])}\n")

def arguments():
    parser = ArgumentParser()
    # parser.set_defaults()

    parser.add_argument('--file_name', default="temp")
    parser.add_argument('--seed', default=1234)
    parser.add_argument('--size_of_file_gb', default=1)
    parser.add_argument('--max_number', default=1000)
    parser.add_argument('--min_number', default = 0)
    parser.add_argument('--mode', default="random")
    parser.add_argument('--number_of_files', default=1)
    return parser.parse_args()
    
if __name__ == "__main__":
    # Generate a file of approximately 1 GB
    # Path for the file to be generated
    file_path = 'random_numbers.txt'
    args = arguments()
    GB_TO_BYTE = 1024*1024*1024
    random.seed(args.seed)
    if args.mode == "random":
        generate_large_file(args.number_of_files, args.file_name, args.size_of_file_gb * GB_TO_BYTE, args.min_number, args.max_number)  
    elif args.mode == "low_entropy":
        generate_large_files_from_low_entropy_distribution(args.number_of_files, args.file_name, args.size_of_file_gb * GB_TO_BYTE, args.min_number, args.max_number)
    print("Done creating...")
    