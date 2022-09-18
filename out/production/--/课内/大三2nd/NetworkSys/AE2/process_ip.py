import os
import subprocess

with open("ipaddr.txt", "r") as f:
    lines = f.readlines()
    for line in lines:
        parts = line.split(" ")
        host = parts[0]
        type = parts[1]
        ip = parts[2].strip("\n")
        output_filename = None
        process = None

        print(parts)

        if type == "IPv4":
            output_filename = "v4-"+host+"-"+ip.replace(".", "")
            process = subprocess.run(['traceroute', '-4', '-q', '1', '-n', ip], check=True, stdout=subprocess.PIPE, universal_newlines=True)
        elif type == "IPv6":
            output_filename = "v6-"+host+"-"+ip.replace(":", "")
            process = subprocess.run(['traceroute', '-6', '-q', '1', '-n', ip], check=True, stdout=subprocess.PIPE, universal_newlines=True)

        output = process.stdout

        with open("separate/" + output_filename, "w") as output_file:
            print(output, output_file)
