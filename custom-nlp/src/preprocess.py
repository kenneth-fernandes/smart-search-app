import spacy

# Load the English spaCy model
nlp = spacy.load("en_core_web_sm")


def preprocess_text(text):
    # Lowercase the text
    doc = nlp(text.lower())

    # Lemmatization and stop words removal
    tokens = [token.lemma_ for token in doc if not token.is_stop and token.is_alpha]

    return tokens
