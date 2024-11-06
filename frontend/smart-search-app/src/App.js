import React from "react";
import SearchBox from "./components/search-box/SearchBox";

function App() {
  
  const handleSearch = (query) => {
        console.log("Search query:", query);
    };
  
  return (
    <div className="App">
      <header>
        <h1>Smart Search App</h1>
        <SearchBox onSearch={handleSearch}/>
      </header>
      {/* Add main components here */}
    </div>
  );
}

export default App;
