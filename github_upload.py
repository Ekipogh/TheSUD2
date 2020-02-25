import sys
import json

import requests

GH_API = "https://api.github.com"
GH_REPO = "{}/repos/Ekipogh/TheSUD2/releases".format(GH_API)
tag_name = sys.argv[1]
branch = sys.argv[2]
token = sys.argv[3]
path_to_artifact = sys.argv[4]
if branch.endswith("master"):
    data_text = """{{
        "tag_name": "{0}",
        "target_commitish": "master",
        "name": "{0}",
        "body": "",
        "draft": false,
        "prerelease": true
    }}""".format(tag_name)
    data = json.loads(data_text)
    print(data)

    r = requests.post(GH_REPO, headers={"Authorization": f"token {token}",
                                        "Content-Type": "application/json"}, json=data)

    print(r.status_code)
    if r.ok:
        id = r.json()["id"]
        data = open(path_to_artifact).read()
        GH_UPLOAD = f"https://uploads.github.com/repos/Ekipogh/TheSUD2/releases/{id}/assets?name=TheSUD2.jar"
        upload = requests.post(GH_UPLOAD, headers={"Authorization": f"token {token}",
                                                   "Content-Type": "application/java-archive"}, data=data)
