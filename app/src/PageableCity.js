import React, {useEffect, useState} from 'react';
import ReactPaginate from 'react-paginate';
import {useQuery} from "@apollo/client";
import {GET_CITIES} from "./queries";
import {Search} from "./components/search";

const editCity = (city) => {
    console.log("Edit " + city.name);
}

const Cities = ({currentCities}) => {
    return (
        <>
            <table className={"table-auto"}>
                <thead>
                <tr>
                    <th>City name</th>
                    <th>City image</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {currentCities &&
                currentCities.map((city) => (
                    <tr>
                        <td>{city.name}</td>
                        <td><img src={city.photo} alt={"City of " + city.name} className="object-scale-down h-48 w-96"/>
                        </td>
                        <td>
                            <button onClick={(event) => editCity(city.name)} className="btn btn--primary ">Edit name
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
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


    return (
        <>
            <Search/>
            <Cities currentCities={currentCities}/>
            <ReactPaginate
                className="flex"
                pageClassName="flex-1"
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