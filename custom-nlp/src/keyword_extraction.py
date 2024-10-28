import spacy

# Load the English spaCy model
nlp = spacy.load("en_core_web_sm")


def extract_keyword(text):
    doc = nlp(text)
    return [token.text for token in doc if token.pos_ in ('NOUN', 'PROPN')]
