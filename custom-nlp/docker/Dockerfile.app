# Dockerfile.app

# Use the dependencies image as the base
FROM dependencies:latest

WORKDIR /app

# Copy the application code
COPY ../src /app/src
COPY ../main.py /app/main.py

# Copy the data folder into the container
COPY ../data /app/data

# Create the model directory in the container
RUN mkdir -p /app/model

# Expose the application port
EXPOSE 8080

# Run the Flask application
CMD ["python", "main.py"]