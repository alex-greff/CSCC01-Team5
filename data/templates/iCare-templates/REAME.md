# iCare Template Format
The purpose of this template format is to match the columns of the iCare xlxs files to fields in a JSON representation of the files data.

Each row entry is mapped to a JSON structure like the following:


```
{
    "field-name1":["col-letter", is-mandatory],
    "field-name2": {
        "sub-field-name1":["col-letter", is-mandatory],
        "sub-field-name2"{
            "sub-sub-field-name1":["col-letter", is-mandatory]
            [and so on...]
        }
    }
}
```

See [client_profile.json](client_profile.json) for an example.