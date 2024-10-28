import json
from src.api import create_app


def smart_search_nlp_handler(event, context):
    try:
        app = create_app()
        data = json.loads(event["body"])
        text = data.get("text", "")
        response = app.test_client().post(
            "/predict", json={"text": text}
        )
        return {
            "statusCode": response.status_code,
            "body": response.data.decode("utf-8")
        }
    except Exception as e:
        print(f"Error: {str(e)}")
        return {
            "statusCode": 500,
            "body": json.dumps({"error": str(e)})
        }
