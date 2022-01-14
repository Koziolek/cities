import {useInput} from "./use_input";
import {useLazyQuery} from "@apollo/client";
import {CITY_BY_NAME} from "../queries";
import {useState} from "react";
import Modal from 'react-modal';

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faWindowClose, faSearch} from '@fortawesome/free-solid-svg-icons';
import {modalStyles} from "./modal_styles";


const CityView = ({foundCity}) => {
    if (foundCity) {
        return (<>
                <h3>{foundCity.name}</h3>
                <img src={foundCity.photo} alt={"City of " + foundCity.name} className="h-48 w-96"/>
                City Found
            </>
        )
    } else {
        return (<h3>No City found :(</h3>)
    }
}

export const Search = () => {
    const [searchResult, setSearchResult] = useState({
        open: false,
        city: null
    });
    const {value: search, bind, reset: resetSearch} = useInput('');
    const [searchQuery, {loading, error}] = useLazyQuery(CITY_BY_NAME, {
        onCompleted: (data) => {
            setSearchResult({
                open: true,
                city: data.cityByName.cities[0]
            });
        }
    });

    if (loading) return null;
    if (error) return null;

    const handleSubmit = (e) => {
        e.preventDefault();
        searchQuery({
            variables: {name: search},
        });
        resetSearch();
    }
    return (
        <>
            <Modal
                isOpen={searchResult.open}
                contentLabel="Search"
                style={modalStyles}
            >
                <button onClick={() => setSearchResult({
                    open: false,
                    city: null
                })}><FontAwesomeIcon icon={faWindowClose}/>
                </button>
                <CityView foundCity={searchResult.city}/>
            </Modal>
            <form onSubmit={handleSubmit}>
                <div className="search-form">
                    <samp><label htmlFor="search">Search by name:</label></samp>
                    <input name="search" id="search" className="search-input" type="text" {...bind}/>
                    <button type="submit" className="btn btn--search">
                        <FontAwesomeIcon icon={faSearch}/>
                    </button>
                </div>
            </form>
        </>
    )
}