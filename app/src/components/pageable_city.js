import React, {useEffect, useState} from 'react';
import ReactPaginate from 'react-paginate';
import Modal from 'react-modal';

import {Search} from "./search";

import {useMutation, useQuery} from "@apollo/client";
import {GET_CITIES, UPDATE_CITY} from "../queries";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faWindowClose} from "@fortawesome/free-solid-svg-icons";

const customStyles = {
    content: {
        top: '25%',
        left: '50%',
        right: 'auto',
        bottom: 'auto',
        minWidth: '50%',
        minHeight: '15%',
        marginRight: '-25%',
        transform: 'translate(-50%, -50%)',
    },
};

const CityEditor = ({open, city, setEditor, updateAction}) => {
    const [updateCity, {}] = useMutation(UPDATE_CITY);
    const closeEditor = () => {
        setEditor({
            open: false,
            city: {name: "", photo: "", id: 0}
        });
    }

    const handleEdit = (data) => {
        data.preventDefault();
        updateCity({
            onCompleted: (data) => {
                updateAction()
            },
            variables: {
                id: data.target.id.value,
                name: data.target.name.value,
                photo: data.target.photo.value
            }
        });

        closeEditor();
    }

    return (
        <>
            <Modal
                isOpen={open}
                contentLabel="Edit City"
                style={customStyles}
            >
                <button onClick={closeEditor}><FontAwesomeIcon icon={faWindowClose}/>
                </button>
                <form onSubmit={handleEdit}>
                    <input type="hidden" value={city.id} id="id" name="id"/>
                    <div>
                        <label htmlFor="name">City name: </label>
                        <input defaultValue={city.name} name="name" id="name" className="form-input"/>
                    </div>
                    <div>
                        <label htmlFor="photo">City photo: </label>
                        <input defaultValue={city.photo} name="photo" id="photo" className="form-input"/>
                    </div>
                    <input type="submit" value="Save" className="btn btn--primary"/>
                    <input type="button" onClick={closeEditor} value="Cancel" className="btn btn--secondary"/>
                </form>
            </Modal>
        </>
    )
}

const Cities = ({currentCities, updateAction}) => {
    const [editor, setEditor] = useState({
        open: false,
        city: {name: "", photo: "", id: 0}
    });

    return (
        <>
            <CityEditor city={editor.city} open={editor.open} setEditor={setEditor} updateAction={updateAction}/>
            <section className="hexagon-gallery">
                {currentCities &&
                currentCities.map((city) => (
                    <div className="hex">
                        <div className="hex-content">
                            <span className="city-name" onClick={() =>
                                setEditor({
                                    open: true,
                                    city: city
                                })
                            }>{city.name}</span>
                        </div>
                        <img src={city.photo} alt={"City of " + city.name} className="object-scale-down h-12"/>
                    </div>
                ))}
            </section>
        </>
    )
}

export const PageableCity = ({citiesPerPage, initialCities, initialPage, initialPageCount}) => {
    const [currentCities, setCurrentCities] = useState(initialCities);
    const [pageCount, setPageCount] = useState(initialPageCount);
    const [currentPage, setCurrentPage] = useState(initialPage);

    const {loading, error, data} = useQuery(GET_CITIES, {
        variables: {page: currentPage, size: 10},
    });
    useEffect(() => {
        if (data && data.cities.cities) {
            setCurrentCities(data.cities.cities);
            setPageCount(data.cities.totalPages);
        }
    }, [currentPage, citiesPerPage, data]);

    if (loading) return null;
    if (error) return null;

    // Invoke when user click to request another page.
    const handlePageClick = (event) => {
        const newPage = event.selected;
        setCurrentPage(newPage);
    };

    const handleUpdate = () => {
        setCurrentPage(currentPage);
    };

    return (
        <>
            <Search/>
            <Cities currentCities={currentCities} updateAction={handleUpdate}/>
            <ReactPaginate
                className="flx"
                pageClassName="flx-1"
                breakLabel="..."
                nextLabel="next >"
                onPageChange={handlePageClick}
                pageRangeDisplayed={5}
                pageCount={pageCount}
                previousLabel="< previous"
                renderOnZeroPageCount={null}
            />
        </>
    );
}