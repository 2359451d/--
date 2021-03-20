import os

base = "routes"
in_dir = base
out_v4_dir = base + "/v4/processed"
out_v6_dir = base + "/v6/processed"

for entry in os.listdir(in_dir+'/v4'):
    prev = ""
    if entry == 'processed':
        continue
    with open(in_dir+'/v4/'+entry, "r") as in_file:
        out_filename = None
        if "v4_" in entry:
            out_filename = out_v4_dir + "/processed-" + entry
        elif "v6_" in entry:
            out_filename = out_v6_dir + "/processed-" + entry

        with open(out_filename, "w") as out_file:
            lines = in_file.readlines()
            for line in lines:
                data = line.split()
                if len(data) == 4:
                    if prev != "":
                        out_file.write("\"" + prev + "\" -- \"" + data[1] + "\"" + "\n")
                    prev = data[1]

for entry in os.listdir(in_dir+'/v6'):
    prev = ""
    if entry == 'processed':
        continue
    with open(in_dir+'/v6/'+entry, "r") as in_file:
        out_filename = None
        if "v4_" in entry:
            out_filename = out_v4_dir + "/processed-" + entry
        elif "v6_" in entry:
            out_filename = out_v6_dir + "/processed-" + entry

        with open(out_filename, "w") as out_file:
            lines = in_file.readlines()
            for line in lines:
                data = line.split()
                if len(data) == 4:
                    if prev != "":
                        out_file.write("\"" + prev + "\" -- \"" + data[1] + "\"" + "\n")
                    prev = data[1]