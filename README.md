
# **Ktor Facts API**

A simple Ktor-based backend API that serves random facts. The application runs on `http://0.0.0.0:8080` and includes an OpenAPI documentation interface.

---

## **Features**
- Retrieve random facts
- OpenAPI documentation (`Swagger UI`) available at `http://0.0.0.0:8080/openapi`
- Admin statistics endpoint (`/admin/statistics`) protected by an API token

---

## **Getting Started**

### **1. Unzip the Repository**
```sh
unzip assignment2025.zip -d assignment2025
cd assignment2025
git log  # Verify commit history is intact
chmod +x ./gradlew  # make the gradlew Gradle helper script executable
```

### **2. Run the Application**
Use Gradle to start the server:
```sh
./gradlew run
```

The server will start at: **`http://0.0.0.0:8080`**

---

## **API Documentation**

### **Swagger UI**
You can explore the API documentation via Swagger UI:  
🔗 [OpenAPI Docs](http://0.0.0.0:8080/openapi)

---

## **Frontend**

See `README.md` in the `./frontend` directory to run frontend application.

## **API Endpoints**

### **Get Random Fact**
- **Endpoint:** `POST /facts`
- **Description:** Fetches a random fact from the Useless Facts API, stores it, and returns it.
- **Example Request:**
  ```sh
  curl -X POST http://0.0.0.0:8080/facts
  ```
- **Example Response:**
```json
{
    "id": "e58a718131824179a6a691b284e57a08",
    "text": "In Disney`s Fantasia, the Sorcerer`s name is \"Yensid\" (Disney backwards.)",
    "source": "djtech.net",
    "sourceUrl": "http://www.djtech.net/humor/useless_facts.htm",
    "language": "en",
    "permalink": "https://uselessfacts.jsph.pl/api/v2/facts/e58a718131824179a6a691b284e57a08",
    "shortId": "HBqTl2c"
}
```

### **Get previously fetched Facts**
- **Endpoint:** `GET /facts`
- **Description:** Returns all cached facts without incrementing the access count.
- **Example Request:**
  ```sh
  curl -X 'GET' \
  'http://0.0.0.0:8080/facts?offset=0&limit=3' \
  -H 'accept: application/json'
  ```
- **Example Response:**
```json
{
  "data": [
    {
      "id": "e58a718131824179a6a691b284e57a08",
      "text": "In Disney`s Fantasia, the Sorcerer`s name is \"Yensid\" (Disney backwards.)",
      "source": "djtech.net",
      "sourceUrl": "http://www.djtech.net/humor/useless_facts.htm",
      "language": "en",
      "permalink": "https://uselessfacts.jsph.pl/api/v2/facts/e58a718131824179a6a691b284e57a08",
      "shortId": "HBqTl2c"
    },
    {
      "id": "c69b900d6bda899e28038ed9905b4165",
      "text": "The eyes of a donkey are positioned so that it can see all four feet at all times.",
      "source": "djtech.net",
      "sourceUrl": "http://www.djtech.net/humor/useless_facts.htm",
      "language": "en",
      "permalink": "https://uselessfacts.jsph.pl/api/v2/facts/c69b900d6bda899e28038ed9905b4165",
      "shortId": "tqlc5Qv"
    },
    {
      "id": "3197bc1e452331d2408c661be1be7748",
      "text": "One quarter of the bones in your body are in your feet.",
      "source": "djtech.net",
      "sourceUrl": "http://www.djtech.net/humor/useless_facts.htm",
      "language": "en",
      "permalink": "https://uselessfacts.jsph.pl/api/v2/facts/3197bc1e452331d2408c661be1be7748",
      "shortId": "UTDXqdw"
    }
  ],
  "count": 45
}
```

---

### **Admin Statistics (Protected Endpoint)**
- **Endpoint:** `GET /admin/statistics`
- **Authentication:** Requires `Authorization` header with API token
- **Example Request:**
  ```sh
    curl -X 'GET' \
    'http://0.0.0.0:8080/admin/statistics?offset=0&limit=4&order=DESC' \
    -H 'accept: application/json' \
    -H 'Authorization: Bearer admin-access-token'
  ```
- **Example Response:**
  ```json
  {
    "data": [
      {
        "fact": {
          "id": "bb2aec8ed6dce43bac1673e6ba55993b",
          "text": "All polar bears are left-handed.",
          "source": "djtech.net",
          "sourceUrl": "http://www.djtech.net/humor/useless_facts.htm",
          "language": "en",
          "permalink": "https://uselessfacts.jsph.pl/api/v2/facts/bb2aec8ed6dce43bac1673e6ba55993b",
          "shortId": "_q2-UF_"
        },
        "accessCount": 16
      },
      {
        "fact": {
          "id": "0cbd0eb26cf1b0f247c32417bcfc74b5",
          "text": "In a lifetime the average human produces enough quarts of spit to fill 2 swimming pools.",
          "source": "djtech.net",
          "sourceUrl": "http://www.djtech.net/humor/useless_facts.htm",
          "language": "en",
          "permalink": "https://uselessfacts.jsph.pl/api/v2/facts/0cbd0eb26cf1b0f247c32417bcfc74b5",
          "shortId": "v3B4Kfr"
        },
        "accessCount": 3
      },
      {
        "fact": {
          "id": "5db4693ed3350b11ddd4366783cc8eec",
          "text": "There are more than 10 million bricks in the Empire State Building!",
          "source": "djtech.net",
          "sourceUrl": "http://www.djtech.net/humor/useless_facts.htm",
          "language": "en",
          "permalink": "https://uselessfacts.jsph.pl/api/v2/facts/5db4693ed3350b11ddd4366783cc8eec",
          "shortId": "BXNsv7z"
        },
        "accessCount": 1
      },
      {
        "fact": {
          "id": "7402a6038f379f7ff5f9e38c895841d7",
          "text": "Most lipstick contains fish scales!",
          "source": "djtech.net",
          "sourceUrl": "http://www.djtech.net/humor/useless_facts.htm",
          "language": "en",
          "permalink": "https://uselessfacts.jsph.pl/api/v2/facts/7402a6038f379f7ff5f9e38c895841d7",
          "shortId": "mmAD--w"
        },
        "accessCount": 1
      }
    ],
    "count": 45
  }
  ```

---

## **Configuration**

### **Environment Variables**
| Variable          | Description           | Default Value |
|------------------|---------------------|--------------|
| `ACCESS_TOKEN`   | API token for admin routes | `admin-access-token` |
| `BASE_URL`       | Application base URL | `http://0.0.0.0:8080` |

---

## **Development & Testing**

### **Run Tests**
```sh
./gradlew test
```
