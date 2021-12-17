import {useInput} from "./use_input";
import {useLazyQuery} from "@apollo/client";
import {CITY_BY_NAME} from "../queries";
import {useState} from "react";
import Modal from 'react-modal';

export const Search = () => {
    const [openSearchResult, setOpenSearchResult] = useState(false);
    const {value: search, bind, reset: resetSearch} = useInput('');
    const [searchQuery, {loading, error, data}] = useLazyQuery(CITY_BY_NAME,{
        onCompleted: (data) =>{
            console.log(data);
            setOpenSearchResult(true);
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
                isOpen={openSearchResult}
                contentLabel="Search"
            >
                <button onClick={() => setOpenSearchResult(false)}>close</button>
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