import unittest
from src.intent_model import train_model, load_model, save_model
import os


class IntentModelTest(unittest.TestCase):
    def test_model_training(self):
        model = train_model('data/intent_classification.csv')
        self.assertIsNotNone(model)

    def test_model_saving_and_loading(self):
        model = train_model('data/intent_classification.csv')
        save_model(model, 'model/test_model.pkl')
        loaded_model = load_model('model/test_model.pkl')
        self.assertEqual(type(model), type(loaded_model))
        os.remove('model/test_model.pkl')  # Cleanup after test


if __name__ == '__main__':
    unittest.main()
