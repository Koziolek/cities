import {useInput} from "./use_input";
import {useLazyQuery} from "@apollo/client";
import {CITY_BY_NAME} from "../queries";
import {useState} from "react";
import Modal from 'react-modal';

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
                city: data.cityByName
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
            >
                <button onClick={() => setSearchResult({
                    open: false,
                    city: null
                })}>close
                </button>
                <CityView foundCity={searchResult.city}/>
            </Modal>
            <form onSubmit={handleSubmit}>
                <div>
                    Search by name: <label><input className="form-input" type="text" {...bind}/></label>
                    <input type="submit" value="Search"/>
                </div>
            </form>
        </>
    )
}