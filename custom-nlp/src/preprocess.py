# preprocess.py

import spacy


# Initialize the model only once and reuse
def load_spacy_model():
    try:
        return spacy.load("en_core_web_sm")
    except Exception as e:
        print(f"Error loading spaCy model: {e}")
        return None


nlp = load_spacy_model()


def preprocess_text(text):
    if not nlp:
        raise RuntimeError("spaCy model not loaded.")

    # Lowercase, lemmatize, and remove stop words
    doc = nlp(text.lower())
    tokens = [token.lemma_ for token in doc if not token.is_stop and token.is_alpha]
    return tokens
