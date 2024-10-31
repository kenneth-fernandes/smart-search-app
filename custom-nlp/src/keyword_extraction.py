# keyword_extraction.py

import spacy
from src.preprocess import load_spacy_model  # Reuse the model loading

nlp = load_spacy_model()


def extract_keyword(text):
    if not nlp:
        raise RuntimeError("spaCy model not loaded.")
    doc = nlp(text)
    return [token.text for token in doc if token.pos_ in ('NOUN', 'PROPN')]