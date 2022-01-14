import {useMutation} from "@apollo/client";
import {UPDATE_CITY} from "../queries";
import Modal from "react-modal";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faWindowClose} from "@fortawesome/free-solid-svg-icons";
import React from "react";
import {modalStyles} from "./modal_styles";

export const CityEditor = ({open, city, setEditor, updateAction}) => {
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
            onCompleted: () => {
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
                style={modalStyles}
                className="modal-pop"
            >
                <button onClick={closeEditor} className="modal-close-btn"><FontAwesomeIcon icon={faWindowClose}/>
                </button>
                <form className="edit-form" onSubmit={handleEdit}>
                    <input type="hidden" value={city.id} id="id" name="id"/>
                    <div className="editor-row">
                        <label htmlFor="name">City name: </label>
                        <input defaultValue={city.name} name="name" id="name" className="form-input" type="text"/>
                    </div>
                    <div className="editor-row">
                        <label htmlFor="photo">City photo: </label>
                        <input defaultValue={city.photo} name="photo" id="photo" className="form-input" type="text"/>
                    </div>
                    <div className="editor-row">
                        <button type="submit" className="btn btn--primary">Save</button>
                        <button onClick={closeEditor} value="Cancel" className="btn btn--secondary">Cancel</button>
                    </div>
                </form>
            </Modal>
        </>
    )
}