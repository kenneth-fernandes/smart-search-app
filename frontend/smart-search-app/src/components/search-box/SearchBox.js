import React, { useState } from "react";
import './SearchBox.css'

const SearchBox = ({ onSearch }) => {
    const [ query, setQuery] = useState('');

    const handleSearch = () => {
        onSearch(query)
    };

    return(<div>
        <input
            type="text"
            placeholder="Search..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            className="searc-input"
        />
        <button onClick={handleSearch} className="search-button">
            Search
        </button>
    </div>);

};

export default SearchBox;