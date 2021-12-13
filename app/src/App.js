import {useEffect, useState} from "react";
import {useQuery} from "@apollo/client";
import {GET_CITIES} from "./queries"

function calculateRange(data, rowsPerPage) {
    return [];
}

function sliceData(data, page, rowsPerPage) {
    return [];
}

const useTable = (data, page, rowsPerPage) => {
    const [tableRange, setTableRange] = useState([]);
    const [slice, setSlice] = useState([]);

    useEffect(() => {
        const range = calculateRange(data, rowsPerPage);
        setTableRange([...range]);

        const slice = sliceData(data, page, rowsPerPage);
        setSlice([...slice]);
    }, [data, setTableRange, page, setSlice]);

    return {slice, range: tableRange};
};

function CityTable({data, rowsPerPage}) {
    const [page, setPage] = useState(1);
    const {slice, range} = useTable(data, page, rowsPerPage);
    return (
        <>
            <table className={"table-auto"}>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Picture</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
            <TableFooter range={range} slice={slice} setPage={setPage} page={page} />
        </>
    );
}

const TableFooter = ({ range, setPage, page, slice }) => {
    useEffect(() => {
        if (slice.length < 1 && page !== 1) {
            setPage(page - 1);
        }
    }, [slice, page, setPage]);
    return (
        <div className={"table-footer-group"}>
            {range.map((el, index) => (
                <button
                    key={index}
                    className={`${".btn"} ${
                        page === el ? ".btn--primary" : ".btn--secondary"
                    }`}
                    onClick={() => setPage(el)}
                >
                    {el}
                </button>
            ))}
        </div>
    );
};

const  App = () => {
    const { loading, error, data } = useQuery(GET_CITIES, {
        variables: {page: 1, size: 10},
    });
    if (loading) return null;
    if (error) return null;

    console.log(data)

    return (
        <div className="App">
            <header className="App-header">
                Simple City List
            </header>
            <CityTable data={data.cities.cities} rowsPerPage={10}/>
        </div>
    );
}

export default App;
