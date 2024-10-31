# api.py

from flask import Flask, request, jsonify
from src.intent_model import load_model
from src.keyword_extraction import extract_keyword
from src.preprocess import preprocess_text

# Load the model once when the app is initialized
model = load_model()  # No path argument


def create_app():
    app = Flask(__name__)

    @app.route('/predict', methods=['POST'])
    def predict():
        data = request.json
        text = data.get("text", "")
        preprocessed_text = " ".join(preprocess_text(text))
        predicted_intent = model.predict([preprocessed_text])[0]
        confidence_score = max(model.predict_proba([preprocessed_text])[0])
        keywords = extract_keyword(text)

        response = {
            "intent": predicted_intent,
            "confidence": round(confidence_score, 2),
            "keywords": keywords
        }

        return jsonify(response)

    return app
