import React, {useEffect, useState} from 'react';
import ReactPaginate from 'react-paginate';

import {Search} from "./search";

import {useQuery} from "@apollo/client";
import {GET_CITIES} from "../queries";
import {CityEditor} from "./city_editor";

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